package block4.cc.iloc.test;

import block4.cc.iloc.Assembler;
import block4.cc.iloc.Simulator;
import block4.cc.iloc.eval.Machine;
import block4.cc.iloc.model.Program;
import block4.cc.iloc.parse.FormatException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MaxTest {

    private final static String BASE_DIR = "block4/cc/iloc/";
    private final static boolean SHOW = true;

    @Test
    public void testParsePrettyPrint() throws FormatException {
        Program p = parse("max");
        assertEquals(Assembler.instance().assemble(p.prettyPrint()), p);
    }

    @Test
    public void testSimulator() {
        Program p = parse("max");
        Simulator sim = new Simulator(p);
        Machine c = sim.getVM();
        c.init("a", 1, 2, 30, 4, 5);
        c.setNum("alength", 5);
        sim.run();
        assertEquals(c.getReg("r_max"), 30);

    }

    Program parse(String filename) {
        File file = new File(filename + ".iloc");
        if (!file.exists()) {
            file = new File(BASE_DIR + filename + ".iloc");
        } try {
            Program result = Assembler.instance().assemble(file);
            String print = result.prettyPrint();
            if (SHOW) {
                System.out.println("Program " + file + " ");
                System.out.println(print);
            }
//            Program other = Assembler.instance().assemble(print);
//            assertEquals(result, other);
            return result;
        } catch (FormatException | IOException e) {
            fail(e.getMessage());
            return null;
        }
    }
}
