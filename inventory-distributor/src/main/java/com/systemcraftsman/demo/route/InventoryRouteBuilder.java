package com.systemcraftsman.demo.route;

import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;

import com.systemcraftsman.demo.model.BookInfo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.BindyType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class InventoryRouteBuilder extends RouteBuilder {

    @ConfigProperty(name = "path", defaultValue = ".")
    String path;

    @ConfigProperty(name = "fileName", defaultValue = "book-inventory.csv")
    String fileName;

    @Override
    public void configure() throws Exception {

        //TODO: Skip any no topics case here.
        
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        from("file://"+path+"?fileName="+fileName+"&noop=true&idempotentKey=${file:name}-${file:modified}")
            .transform(body())
            .unmarshal()
            .bindy(BindyType.Csv, BookInfo.class)
            .split(body()).parallelProcessing()
            .setHeader(KafkaConstants.KEY, simple("${body.isbn}"))
            .choice()
                .when(simple("${body.storeLocation} == 'london'"))
                    .marshal(new JacksonDataFormat(BookInfo.class))
                    .log("Message sent: ${body}")
                    .to("kafka:london-inventory")
                .when(simple("${body.storeLocation} == 'newyork'"))
                    .marshal(new JacksonDataFormat(BookInfo.class))
                    .log("Message sent: ${body}")
                    .to("kafka:newyork-inventory")
                .when(simple("${body.storeLocation} == 'istanbul'"))
                    .marshal(new JacksonDataFormat(BookInfo.class))
                    .log("Message sent: ${body}")
                    .to("kafka:istanbul-inventory")
                .otherwise()
                    .log("No store location is defined for ${body}");
    }
}

