import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration
public class EasyIMServerApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EasyIMServerApplication.class,args);
    }


    @Override
    public void run(String... strings) throws Exception {

    }
}
