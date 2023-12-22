package frc.robot.subsystems.arm;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class ArmIOSparkMax implements ArmIO {
    private CANSparkMax leader;
    private CANSparkMax follower;
    private DutyCycleEncoder encoder;
    private final double zeroDegrees = 36;
    private double prevAngle;
    private double setpoint;

    public ArmIOSparkMax() {
        leader = new CANSparkMax(1, MotorType.kBrushless);
        leader.getPIDController().setP(.1);
        follower = new CANSparkMax(2, MotorType.kBrushless);
        follower.follow(leader);

        encoder = new DutyCycleEncoder(3);

        prevAngle = zeroDegrees;
    }

    public void updateInputs(ArmIOInputs inputs) {
        prevAngle = inputs.angleDegrees;
        inputs.rawAngleDegrees = encoder.get();
        inputs.angleDegrees = inputs.rawAngleDegrees - zeroDegrees;
        inputs.setpoint = setpoint;
        inputs.state = Arm.getCurrentState().toString();
        inputs.desiredPosition = Arm.getDesiredPosition().toString();

        // deltaAngle/deltaTime is a rough approximation of omega, the angular velocity
        inputs.angleVelDegreesPerSecond = (inputs.angleDegrees - prevAngle) / .02;
    }

    public void updateSetpoint(double setpoint) {
        leader.getPIDController().setReference(setpoint, ControlType.kPosition);
        this.setpoint = setpoint;
    }

    public double getPositionDegrees() {
        return encoder.get() - zeroDegrees;
    }
}
