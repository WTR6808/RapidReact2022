package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private CANSparkMax intakeMotor = new CANSparkMax(Constants.INTAKE_PORT, MotorType.kBrushless);

  public Intake() {
    intakeMotor.restoreFactoryDefaults();
  }
  public void intakeOn(){
    intakeMotor.set(Constants.INTAKE_SPEED);
  }
  public void intakeOff(){
    intakeMotor.set(0);
  }
  public void intakeReverse(){
    intakeMotor.set((-Constants.INTAKE_SPEED));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake Output Current", intakeMotor.getOutputCurrent());
  }
}
