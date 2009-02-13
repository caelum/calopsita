package br.com.caelum.calopsita.tasks;

import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyStartTask extends Task {

    static final int SHUTDOWN = 1;
    private final List<Context> contexts = new ArrayList<Context>();

    public void setWaitForShutdown(boolean waitForShutdown) {
        this.waitForShutdown = waitForShutdown;
    }

    private boolean waitForShutdown;
    private int port;
    private String databasePort;
    private Server server;

    public void setPort(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        final JettyStartTask task = new JettyStartTask();
        task.setPort(Integer.parseInt(args[0]));
        FileWriter fw = new FileWriter("log_jetty_" + task.port);
        PrintWriter pw = new PrintWriter(fw, true);
        task.setDatabasePort(args[1]);
        task.setWaitForShutdown(false);
        Context ctx = new Context();
        ctx.setContext("/calopsita");
        ctx.setWar(args[2]);
        task.addContext(ctx);
        Thread t = new Thread() {
            @Override
            public void run() {
                task.execute();
            }
        };
        t.start();
        ServerSocket ss = new ServerSocket(task.port + 100);
        pw.println("Started and waiting for socket shutdown");
        while (true) {
            Socket s = ss.accept();
            pw.println("Accepted a new socket");
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            int val = ois.readInt();
            pw.println("Read " + val);
            s.close();
            if (val == SHUTDOWN) {
                break;
            }
        }
        pw.println("Stopping server at port " + task.port);
        task.server.stop();
        pw.println("Stopped server at port " + task.port);
        pw.close();
        fw.close();
    }

    @Override
    public void execute() throws BuildException {

        this.server = new Server(port);

        try {
            Thread.currentThread().setContextClassLoader(
                    this.getClass().getClassLoader());
            List<Handler> lista = new ArrayList<Handler>();
            for (Context ctx : this.contexts) {
                WebAppContext wac = new WebAppContext();
                wac.setAttribute("databasePort", this.databasePort);
                wac.setParentLoaderPriority(true);
                wac.setContextPath(ctx.getContext());
                wac.setWar(ctx.getWar());
                lista.add(wac);
            }
            lista.add(new DefaultHandler());
            HandlerCollection handlers = new HandlerCollection();
            handlers.setHandlers(lista.toArray(new Handler[] {}));
            server.setHandler(handlers);
            server.setStopAtShutdown(true);
            server.start();
            if (waitForShutdown) {
                server.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }

    public void addContext(Context ctx) {
        this.contexts.add(ctx);
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

}
