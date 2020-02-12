package pp.s1855034.q2_2;

import block4.cc.iloc.*;
import block4.cc.iloc.eval.Machine;
import block4.cc.iloc.model.Program;
import block4.cc.iloc.parse.FormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class FindTTest {
    private Assembler assembler = Assembler.instance();
    private Simulator sim;
    private final static boolean SHOW = false;

    @Before
    public void before() {
        Program p = assemble("pp/s1855034/q2_2/find-t");
        if (SHOW) {
            System.out.println(p.prettyPrint());
        }
        Machine vm = new Machine();
        this.sim = new Simulator(p, vm);
    }

    @Test
    public void testFirst() {
        run(sim, 1, new int[]{5, 1, 2, 3, 4, 5},  0);
        run(sim, 5, new int[]{5, 5, 5, 6, 7, 8},  0);
    }

    @Test
    public void testLast() {
        run(sim, 56, new int[]{5, 12, 23, 34, 45, 56},  4);
        run(sim, 9, new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8, 9},  8);
    }

    @Test
    public void testFound() {
        run(sim, 34, new int[]{5, 14, 34, 39, 78, 111},  1);
        run(sim, 2, new int[]{14, 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233}, 3);
        run(sim, 55, new int[]{14, 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233}, 10);
        run(sim, 89, new int[]{14, 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233}, 11);
    }

    @Test
    public void testNegative() {
        run(sim, -2, new int[]{5, -10, -6, -2, 0, 2},  2);
    }

    @Test
    public void testNotFound() {
        // output is -1 if t was not found
        run(sim, 8, new int[]{9, 5, 5, 5, 6, 6, 6, 30, 30, 30}, -1);
        run(sim, 3, new int[]{0}, -1);
        run(sim, 1, new int[]{1, 2}, -1);
    }

    private void run(Simulator sim, int t, int[] a, int output) {
        Machine vm = sim.getVM();
        vm.clear();
        vm.setNum("t", t);
        vm.init("a", a);
        sim.run();
        if (SHOW) {
            System.out.println(vm);
        }
        assertEquals(output, vm.getReg("r_m"));
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
