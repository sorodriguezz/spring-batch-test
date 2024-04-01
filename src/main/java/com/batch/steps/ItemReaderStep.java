package com.batch.steps;

import com.batch.entities.Person;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ItemReaderStep implements Tasklet {

    @Autowired // lo necesita desde el BatchConfig.class
    private ResourceLoader resourceLoader; // importar archivos desde resource

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("---> INICIO paso lectura <---");

        // "classpath:" devuelve la ruta abssoluta hasta el directorio "resources"
        Reader reader = new FileReader(resourceLoader.getResource("classpath:files/destination/persons.csv").getFile());

        CSVParser csvParser = new CSVParserBuilder() // configuración para leer archivo separado por ","
                .withSeparator(',')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(csvParser)
                .withSkipLines(1) // no leer titulo
                .build();

        List<Person> personList = new ArrayList<>();

        String[] actualLine;

        while((actualLine = csvReader.readNext()) != null) {
            Person person = new Person();
            person.setName(actualLine[0]);
            person.setLastName(actualLine[1]);
            person.setAge(Integer.parseInt(actualLine[2]));

            personList.add(person);
        }

        csvReader.close();
        reader.close();

        log.info("---> FIN paso lectura <---");

        return RepeatStatus.FINISHED;
    }
}
