package dev.mattrm.mc.gametools.util.version;

public interface ITestUtil extends IVersioned {
    static ITestUtil get() {
        return (ITestUtil) VersionDependentClasses.get(ITestUtil.class);
    }
}
