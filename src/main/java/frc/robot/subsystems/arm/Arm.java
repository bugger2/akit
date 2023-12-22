package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import static frc.robot.subsystems.arm.Arm.ArmStates.*;

public class Arm extends SubsystemBase {
    private ArmIO io;
    private ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

    protected enum ArmStates {
        INIT,
        MOVING,
        IDLE
    }

    private static ArmStates currentState = IDLE;
    private static ArmStates newState = INIT;

    public enum ArmPositions {
        HOME(0),
        LOW(10),
        MID(30),
        HIGH(45);

        public double angleDegrees;

        ArmPositions(double angleDegrees) {
            this.angleDegrees = angleDegrees;
        }
    }

    private static ArmPositions desiredPosition = ArmPositions.HOME;

    private final double errorDegrees = 2;
    private MechanismLigament2d ligament;

    public Arm() {
        ligament = RobotContainer.mechanism2d.getRoot("arm", 1.5, 1.5)
            .append(new MechanismLigament2d("arm", 0.5, 0));

        io = Constants.isReal ? new ArmIOSparkMax() : new ArmIOSim();
        Logger.getInstance().processInputs(Constants.logPrefix + "Arm", inputs);
    }

    @Override
    public void periodic() {
        if(currentState != newState) {
            switch(newState) {
                case INIT:
                    break;
                case MOVING:
                    io.updateSetpoint(desiredPosition.angleDegrees);
                    break;
                case IDLE:
                    break;
            }

            currentState = newState;
        }

        switch(currentState) {
            case INIT:
                newState = IDLE;
                break;
            case MOVING:
                if(inPosition()) {
                    newState = IDLE;
                }
                break;
            case IDLE:
                if(!inPosition()) {
                    newState = MOVING;
                }
                break;
        }

        ligament.setAngle(io.getPositionDegrees());

        io.updateInputs(inputs);
        Logger.getInstance().processInputs(Constants.logPrefix + "Arm", inputs);
    }

    public static ArmStates getCurrentState() {
        return currentState;
    }

    public static ArmPositions getDesiredPosition() {
        return desiredPosition;
    }

    private boolean inPosition() {
        return Math.abs(io.getPositionDegrees() - desiredPosition.angleDegrees) < errorDegrees;
    }

    public Command goTo(ArmPositions position) {
        return runOnce(() -> {
            if(!position.equals(desiredPosition)) {
                desiredPosition = position;
                io.updateSetpoint(desiredPosition.angleDegrees);
            }
        });
    }
}
