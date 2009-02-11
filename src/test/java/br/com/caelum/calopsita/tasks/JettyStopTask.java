package br.com.caelum.calopsita.tasks;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class JettyStopTask extends Task {

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void execute() throws BuildException {

        try {
            Socket s = new Socket("localhost", port);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeInt(JettyStartTask.SHUTDOWN);
            oos.flush();
            s.close();
        } catch (IOException e) {
            throw new BuildException("Unable to shutdown server at port "
                    + port, e);
        }
    }

}
