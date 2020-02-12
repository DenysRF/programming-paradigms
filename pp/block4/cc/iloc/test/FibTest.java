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

public class FibTest {

    private final static String BASE_DIR = "block4/cc/iloc/";
    private final static boolean SHOW = true;

    @Test
    public void testFibReg() {
        Program p = parse("fibReg");
        Simulator sim = new Simulator(p);
        Machine c = sim.getVM();

//        c.setNum("n", 2);
//        sim.run();
//        assertEquals(1, c.getReg("r_z"));

        c.setNum("n", 7);
        sim.run();
        assertEquals(c.getReg("r_z"), 13);

//        c.setNum("n", 46);
//        sim.run();
//        assertEquals(1836311903, c.getReg("r_z"));

//        c.setNum("n", 47);
//        sim.run();
//        assertEquals(2971215073, c.getReg("r_z"));
    }

    @Test
    public void testFibMem() {
        Program p = parse("fibMem");
        Simulator sim = new Simulator(p);
        Machine c = sim.getVM();

        c.init("n", 7);
        c.init("x", 0);
        c.init("y", 1);
        c.init("z", 1);
        sim.run();
        assertEquals(13, c.getReg("r1"));

//        c.init("n", 46);
//        c.init("x", 0);
//        c.init("y", 1);
//        c.init("z", 1);
//        sim.run();
//        assertEquals(1836311903, c.getReg("r1"));

//        c.init("n", 47);
//        c.init("x", 0);
//        c.init("y", 1);
//        c.init("z", 1);
//        sim.run();
//        assertEquals(2971215073, c.getReg("r_z"));
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
            return result;
        } catch (FormatException | IOException e) {
            fail(e.getMessage());
            return null;
        }
    }

}
