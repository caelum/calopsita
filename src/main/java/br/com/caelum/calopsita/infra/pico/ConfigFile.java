package br.com.caelum.calopsita.infra.pico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.vraptor.config.ConfigException;
import org.vraptor.scope.ApplicationContext;

public class ConfigFile {
    private final Properties properties;

    public static ConfigFile atWebAppRoot(final String relativePath,
            final ApplicationContext webAppContext) throws ConfigException {
        return new ConfigFile(readPropertiesFile(Paths.fullPathFromWebApplicationRoot(
                webAppContext, relativePath)));
    }

    private static Properties readPropertiesFile(final String path) throws ConfigException {
        FileInputStream in = null;
        try {
            final Properties props = new Properties();
            in = new FileInputStream(path);
            props.load(in);
            return props;
        } catch (final FileNotFoundException e) {
            throw new ConfigException("Could not find configuration file at path " + path, e);
        } catch (final IOException e) {
            throw new ConfigException("Exception reading configuration file", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    throw new ConfigException(e);
                }
            }
        }
    }

    static String ensureDoesNotEndWithSlash(String path) {
        final char lastChar = path.charAt(path.length() - 1);

        if (lastChar == '/' || lastChar == '\\') {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    static String ensureStartWithSlash(String s) {
        final char firstChar = s.charAt(0);

        if (firstChar != '/' && firstChar != '\\') {
            s = "/" + s;
        }

        return s;
    }

    ConfigFile(final Properties properties) {
        this.properties = properties;
    }

    public Set<Entry<String, String>> entrySet() {
        final HashSet<Entry<String, String>> entries = new HashSet<Entry<String, String>>();
        for (final Entry<Object, Object> entry : properties.entrySet()) {
            entries.add(new Entry<String, String>() {
                public String getKey() {
                    return entry.getKey().toString();
                }

                public String getValue() {
                    return entry.getValue().toString();
                }

                public String setValue(final String value) {
                    return value;
                }

                public boolean equals(Object obj) {
                    if ((obj == null) || !(obj instanceof Entry))
                        return false;

                    return entry.equals(this);
                }

                public int hashCode() {
                    return getKey().hashCode() + (31 * getValue().hashCode());
                }
            });
        }
        return entries;
    }

    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

}
