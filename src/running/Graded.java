//DO NOT MODIFY

package running;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Graded {
	public String description();
	public int marks();
}
