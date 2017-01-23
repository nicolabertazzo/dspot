package fr.inria.diversify.mutant.pit;

import fr.inria.diversify.Utils;
import fr.inria.diversify.buildSystem.android.InvalidSdkException;
import fr.inria.diversify.dspot.*;
import fr.inria.diversify.dspot.support.DSpotCompiler;
import fr.inria.diversify.runner.InputConfiguration;
import fr.inria.diversify.runner.InputProgram;
import fr.inria.diversify.util.FileUtils;
import fr.inria.diversify.util.InitUtils;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 1/5/17
 */
public class PitTest extends MavenAbstractTest {

    @Override
    public String getPathToPropertiesFile() {
        return "src/test/resources/test-projects/test-projects.properties";
    }

    @Test
    public void testPit() throws Exception, InvalidSdkException {

        /*
            Run the PitRunner on the test-project example.
                Checks that the PitRunner return well the results, and verify state of mutant.
         */
        AmplificationHelper.setSeedRandom(23L);
        Utils.init(this.getPathToPropertiesFile());
        List<PitResult> pitResults = PitRunner.run(Utils.getInputProgram(), Utils.getInputConfiguration(),
                Utils.getInputProgram().getFactory().Class().get("example.TestSuiteExample"));

        assertTrue(null != pitResults);
        assertEquals(9, pitResults.stream().filter(pitResult -> pitResult.getStateOfMutant() == PitResult.State.SURVIVED).count());
        Optional<PitResult> OptResult = pitResults.stream().filter(pitResult -> pitResult.getStateOfMutant() == PitResult.State.SURVIVED).findFirst();
        assertTrue(OptResult.isPresent());
        PitResult result = OptResult.get();
        assertEquals("org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator", result.getFullQualifiedNameMutantOperator());
        assertEquals(null, result.getTestCaseMethod());
        assertEquals(27, result.getLineNumber());
        assertEquals("<init>", result.getLocation());

        assertEquals(13, pitResults.stream().filter(pitResult -> pitResult.getStateOfMutant() == PitResult.State.KILLED).count());
        OptResult = pitResults.stream().filter(pitResult -> pitResult.getStateOfMutant() == PitResult.State.KILLED).findFirst();
        assertTrue(OptResult.isPresent());
        result = OptResult.get();
        assertEquals("org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator", result.getFullQualifiedNameMutantOperator());
        assertEquals("test4", result.getTestCaseMethod().getSimpleName());
        assertEquals(18, result.getLineNumber());
        assertEquals("charAt", result.getLocation());

        assertEquals(3, pitResults.stream().filter(pitResult -> pitResult.getStateOfMutant() == PitResult.State.NO_COVERAGE).count());
    }

    @Test
    public void testFailPit() throws Exception, InvalidSdkException {

        /*
            Run the PitRunner in wrong configuration.
         */
        try {
            List<PitResult> pitResults = PitRunner.run(null, null, null);
            fail("PirRunner.run() should throw : java.lang.NullPointerException");
        } catch (Exception e) {
            //catch the null pointer exception
        }
    }
}