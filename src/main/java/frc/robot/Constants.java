package frc.robot;

import java.io.File;

public class Constants {
    public static final boolean isReal = new File("/home/lvuser/deploy").exists();
    public static final boolean isReplay = false; // if program is a replay or regular sim.
                                                  // Does not affect real robot program
    public static final boolean isSim = !isReplay && !isReal;

    public static final String logPrefix = (isReal || isSim) ? "RealOutputs/" : "ReplayOutputs";
}
