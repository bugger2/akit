package frc.robot.subsystems.arm;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class ArmIOSim implements ArmIO {
    SingleJointedArmSim armSim;
    PIDController controller;
    double prevAngle;
    double setpoint;

    public ArmIOSim() {
        armSim = new SingleJointedArmSim(
            DCMotor.getNEO(2),
            100,
            SingleJointedArmSim.estimateMOI(0.5, 5),
            0.5,
            0,
            Math.PI/2,
            true);

        controller = new PIDController(0.5, 0, 0);
    }

    @Override
    public void updateInputs(ArmIOInputs inputs) {
        prevAngle = inputs.angleDegrees;

        armSim.setInput(controller.calculate(prevAngle, setpoint));
        armSim.update(.02);

        inputs.angleDegrees = Math.toDegrees(armSim.getAngleRads());
        inputs.angleVelDegreesPerSecond = Math.toDegrees(armSim.getVelocityRadPerSec());
        inputs.desiredPosition = Arm.getDesiredPosition().toString();
        inputs.state = Arm.getCurrentState().toString();
        inputs.rawAngleDegrees = inputs.angleDegrees;
        inputs.setpoint = setpoint;
    }

    @Override
    public void updateSetpoint(double setpoint) {
        controller.setSetpoint(setpoint);
        this.setpoint = setpoint;
    }

    @Override
    public double getPositionDegrees() {
        return Math.toDegrees(armSim.getAngleRads());
    }
}
