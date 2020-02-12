package pp.s1855034.q2_4;

import block4.cc.iloc.Assembler;
import block4.cc.iloc.Simulator;
import block4.cc.iloc.eval.Machine;
import block4.cc.iloc.model.Program;
import block4.cc.iloc.parse.FormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class Iter_fibTest {

    private Assembler assembler = Assembler.instance();
    private Simulator sim;
    private final static boolean SHOW = false;

    @Before
    public void before() {
        Program p = assemble("pp/s1855034/q2_4/iter_fib");
        if (SHOW) {
            System.out.println(p.prettyPrint());
        }
        Machine vm = new Machine();
        this.sim = new Simulator(p, vm);
    }

    @Test
    public void test() {
        run(sim,1, 1);
        run(sim,4, 3);
        run(sim,8, 21);
        run(sim,20, 6765);
    }

    private void run(Simulator sim, int input, int output) {
        Machine vm = sim.getVM();
        vm.clear();
        vm.setNum("n", input);
        sim.run();
        if (SHOW) {
            System.out.println(vm);
        }
        assertEquals(output, vm.getReg("r_return_value"));
    }

    private Program assemble(String filename) {
        File file = new File(filename + ".iloc");
        try {
            return this.assembler.assemble(file);
        } catch (FormatException | IOException e) {
            fail(e.getMessage());
            return null;
        }
    }
}
