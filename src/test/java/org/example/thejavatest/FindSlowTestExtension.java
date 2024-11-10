package org.example.thejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    //    private static final long THRESHOLD = 1000L;
    private long THRESHOLD;

    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        ExtensionContext.Store store = getStore(extensionContext);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        SlowTest annotation = extensionContext.getRequiredTestMethod().getAnnotation(SlowTest.class);

        ExtensionContext.Store store = getStore(extensionContext);
        Long startTime = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - startTime;
        if (duration > THRESHOLD && annotation == null) {
            System.out.printf("<<<Please consider mark method [%s] with @SlowTest>>>\n", extensionContext.getRequiredTestMethod().getName());
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext extensionContext) {
        //ExtensionContext 에는 값들을 저장할 수 있는 store 가 있다.
        String testClassName = extensionContext.getRequiredTestClass().getName();
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
        return store;
    }
}
