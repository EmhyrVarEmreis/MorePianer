package pl.morecraft.dev.morepianer.app.devutil;

import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConfigurationWriter {

    public ArrayList<String> beanDefinitions = new ArrayList<String>();

    public ConfigurationWriter(ConfigurableApplicationContext context, String file) {
        String[] tab = context.getBeanDefinitionNames();
        for (int i = 0; i < tab.length - 6; i++) {
            Class clazz = context.getType(tab[i]);
            String scope = context.isPrototype(tab[i]) ? "prototype" : "singleton";
            String s = "<bean id=\"" + tab[i] + "\" class=\"" + clazz.getName() + "\" scope=\"" + scope + "\"/>";
            beanDefinitions.add(s);
        }

        try {
            generateConfiguration(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("restriction")
    public void generateConfiguration(String file) throws FileNotFoundException {
        File xmlConfig = new File(file);
        PrintWriter printer = new PrintWriter(xmlConfig);

        generateHeader(printer);

        generateCorpse(printer);

        generateTail(printer);

        printer.checkError();

    }

    @SuppressWarnings("restriction")
    private void generateCorpse(PrintWriter printer) {

        for (String beanPath : beanDefinitions) {
            printer.println(beanPath);
        }

    }

    @SuppressWarnings("restriction")
    private void generateHeader(PrintWriter printer) {
        printer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        printer.println("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
        printer.println("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        printer.println("xmlns:context=\"http://www.springframework.org/schema/context\"");
        printer.println("xsi:schemaLocation=\"");
        printer.println("http://www.springframework.org/schema/mvc");
        printer.println("http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd");
        printer.println("http://www.springframework.org/schema/beans");
        printer.println("http://www.springframework.org/schema/beans/spring-beans-3.0.xsd");
        printer.println("http://www.springframework.org/schema/context");
        printer.println("http://www.springframework.org/schema/context/spring-context-3.0.xsd\"");
        printer.println("default-lazy-init=\"true\">");
    }

    @SuppressWarnings("restriction")
    private void generateTail(PrintWriter printer) {
        // printer.println("<bean class=\"com.xxx.frmwrk.spring.processors.xxxBeanFactoryPostProcessor\"/>");
        printer.println("<bean class=\"com.xxx.frmwrk.spring.processors.xxxPostProcessor\"/>");
        printer.println("</beans>");
    }

}