/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 *
 * @author User
 */
public class VelocityBuildReq {

    public void initialize() {
        Properties props = getProperties();
        System.out.println(props);

        Velocity.init(props);

    }

    public String generateReport(Map dataMap, String templateName) {
        String req = null;
        initialize();
        VelocityContext context = new VelocityContext();

        
        setContextValues(dataMap, context);
        try {
            req = mergeTemplate(templateName, context);
        } catch (MethodInvocationException | IOException | ResourceNotFoundException | ParseErrorException ex) {
            ex.printStackTrace();
        }
        return req;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        props.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        return props;
    }

    private void setContextValues(Map dataMap, VelocityContext context) {
        

        Set<String> keys = dataMap.keySet();

        Iterator<String> it = keys.iterator();

        while (it.hasNext()) {
            String key = it.next();
            context.put(key, dataMap.get(key));
        }
        
        
    }

    private String mergeTemplate(String templateName, VelocityContext context) throws MethodInvocationException, IOException, ResourceNotFoundException, ParseErrorException {
        Template template = null;

        try {
            template = Velocity.getTemplate(templateName +".vm");

        } catch (ResourceNotFoundException rnfe) {
            System.out.println("Example : error : cannot find template " + rnfe);
        } catch (ParseErrorException pee) {
            System.out.println("Example : Syntax error in template :" + pee);
        }
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        System.out.println(writer.toString());
        String reqxml = writer.toString();

        return reqxml;
    }
}
