package frc.robot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.Arm.ArmPositions;
import frc.robot.subsystems.claw.Claw;

public class RobotContainer {
    CommandXboxController controller;
    Claw claw;
    Arm arm;
    public static final Mechanism2d mechanism2d = new Mechanism2d(3, 3);;

    public RobotContainer() {
        claw = new Claw();
        arm = new Arm();
        controller = new CommandXboxController(0);

        controllerBindings();
    }

    private void controllerBindings() {
        controller.a().onTrue(arm.goTo(ArmPositions.HOME));
        controller.b().onTrue(arm.goTo(ArmPositions.HIGH));
        controller.x().onTrue(claw.suck());
        controller.y().onTrue(claw.spit());
    }

    public static void updateLogs() {
        Logger.getInstance().recordOutput("RobotVisualization", mechanism2d);
    }
}
