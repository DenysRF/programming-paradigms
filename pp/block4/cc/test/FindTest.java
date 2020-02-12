package block4.cc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import block4.cc.iloc.Assembler;
import block4.cc.iloc.Simulator;
import block4.cc.iloc.eval.Machine;
import block4.cc.iloc.model.Program;
import block4.cc.iloc.parse.FormatException;
@SuppressWarnings("javadoc")
public class FindTest {
	private Assembler assembler = Assembler.instance();
	private final static boolean SHOW = true;

	@Test(timeout = 1000)
	public void simulate() {
		Program p = assemble("block4/cc/iloc/find");
		if (SHOW) {
			System.out.println(p.prettyPrint());
		}
		Machine vm = new Machine();
		Simulator sim = new Simulator(p, vm);
		run(sim, 15, 2);
		run(sim, 5, 0);
		run(sim, 10, 3);
	}

	private void run(Simulator sim, int input, int output) {
		Machine vm = sim.getVM();
		vm.clear();
		vm.setNum("alength", 3);
		vm.init("a", 5, 2, 15);
		sim.setIn(new ByteArrayInputStream(("" + input).getBytes()));
		sim.run();
		if (SHOW) {
			System.out.println(vm);
		}
		assertEquals(output, vm.getReg("r_i"));
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
