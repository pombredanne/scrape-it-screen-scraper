package scrape.it.utilities;

import java.security.SecureRandom;
import java.util.Random;

public class Tokenizer {
	   public int Tokenizer() throws Exception{
           SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
           Random random2 = new Random();
           random.setSeed(random2.nextInt(2000));
           int randInt = random.nextInt(1000);
           int randInt2 = random.nextInt(21424);
           int rando = randInt + randInt2;
           return rando;
}
}
