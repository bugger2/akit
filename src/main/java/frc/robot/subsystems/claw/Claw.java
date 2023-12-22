package frc.robot.subsystems.claw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

import org.littletonrobotics.junction.Logger;

public class Claw extends SubsystemBase {
    protected enum ClawStates {
        INIT,
        SUCKING,
        SPITTING,
        IDLE;
    }

    private static ClawStates currentState = ClawStates.IDLE;
    private static ClawStates newState = ClawStates.INIT;

    private Timer timer;
    

    ClawIO io; 
    ClawIOInputsAutoLogged inputs = new ClawIOInputsAutoLogged();

    private static final double kSuckSpeed = .7;
    private static final double kSpitSpeed = -1;
    private static final String logPath = Constants.logPrefix + "Claw";
    private static final double timeToActuate = 1.5;

    public Claw() {
        io = new ClawIOSparkMax();
        Logger.getInstance().processInputs(logPath, inputs);
    }

    @Override
    public void periodic() {
        if(currentState != newState) {
            switch(newState) {
                case INIT:
                    break;
                case SUCKING:
                    timer = new Timer();
                    io.setMotorSpeed(kSuckSpeed);
                    break;
                case SPITTING:
                    timer = new Timer();
                    io.setMotorSpeed(kSpitSpeed);
                    break;
                case IDLE:
                    io.setMotorSpeed(0);
                    break;
            }

            currentState = newState;
        }

        switch(currentState) {
            case INIT:
                newState = ClawStates.IDLE;
                break;
            case IDLE:
                break;
            default:
                if(timer.get() > timeToActuate) {
                    newState = ClawStates.IDLE;
                }
                break;
        }
        
        io.updateInputs(inputs);
        Logger.getInstance().processInputs(logPath, inputs);
    }

    public Command suck() {
        return runOnce(() -> Claw.newState = ClawStates.SUCKING);
    }

    public Command spit() {
        return runOnce(() -> Claw.newState = ClawStates.SPITTING);
    }

    public Command stop() {
        return runOnce(() -> Claw.newState = ClawStates.IDLE);
    }

    public static ClawStates getCurrentState() {
        return currentState;
    }
}
