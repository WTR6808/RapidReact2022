// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.DropIntake;
import frc.robot.commands.Drive.GetWithinDistancePID;
import frc.robot.commands.Drive.RotatePID;
import frc.robot.commands.Intake.IntakeOFF;
import frc.robot.commands.Intake.IntakeON;
import frc.robot.commands.Shooter.GateOpen;
import frc.robot.commands.Shooter.ShooterHigh;
import frc.robot.commands.Shooter.ShooterStop;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html


public class TestAuto extends SequentialCommandGroup {
  /** Creates a new TestAuto. */
  
  public TestAuto(Drivetrain drivetrain, Intake intake, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

   // addCommands();

   super(
     sequence(
       new DropIntake(drivetrain),
       new IntakeON(intake),
       new DriveDistance(drivetrain, 0.6, 40),
       new ShooterHigh(shooter),
       new WaitCommand(1),
       new RotatePID(drivetrain, 180),
       new IntakeOFF(intake),
       new GetWithinDistancePID(drivetrain,108),
       new GateOpen(shooter),
       new WaitCommand(2),
       new ShooterStop(shooter)


       
   ));
  }
}
