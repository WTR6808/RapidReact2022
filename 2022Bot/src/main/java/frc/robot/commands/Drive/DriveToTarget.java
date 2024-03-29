// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveToTarget extends CommandBase {
  private Drivetrain d;
  private boolean atTarget = false;
  /** Creates a new DriveToTarget. */
  public DriveToTarget(Drivetrain drivetrain) {
    d = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    d.stop();
    d.setVisionMode();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    atTarget = !d.visionDriveToTarget();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    d.stop();
    d.setDriverMode();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return atTarget;
  }
}
