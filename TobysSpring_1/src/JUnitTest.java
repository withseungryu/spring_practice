import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.HashSet;
import java.util.Set;

public class JUnitTest {
//	static JUnitTest testObject;
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	
	@Test 
	public void test1() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
	}
	
	@Test
	public void test2() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
	}
	
	@Test
	public void test3() {
//		assertThat(this, is(not(sameInstance(testObject))));
//		testObject = this;
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
	}
}
