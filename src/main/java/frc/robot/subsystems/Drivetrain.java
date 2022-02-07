// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  private WPI_VictorSPX topLeft = new WPI_VictorSPX (Constants.TOP_LEFT);
  private WPI_VictorSPX topRight = new WPI_VictorSPX (Constants.TOP_RIGHT);
  private WPI_VictorSPX bottomRight = new WPI_VictorSPX (Constants.BOTTOM_RIGHT);
  private WPI_VictorSPX bottomLeft = new WPI_VictorSPX (Constants.BOTTOM_LEFT);
  private DifferentialDrive DriveTrain = new DifferentialDrive (topLeft, topRight);
  private boolean isReversed = false;

  private Encoder leftEncoder = new Encoder(Constants.encoder_LeftA, Constants.encoder_LeftB,
   true, CounterBase.EncodingType.k4X);
  private Encoder rightEncoder = new Encoder(Constants.encoder_RightA, Constants.encoder_RightB, 
  false, CounterBase.EncodingType.k4X);

  private final AnalogInput ultrasonic = new AnalogInput(Constants.ULTRASONIC_PORT);
  private MedianFilter m_filter = new MedianFilter(5);

  public Drivetrain() {
    topLeft.configFactoryDefault() ;
    bottomLeft.configFactoryDefault () ;
    topRight.configFactoryDefault () ;
    bottomRight.configFactoryDefault () ; 

    bottomLeft.follow(topLeft); 
    bottomRight.follow(topRight);

    topRight.setInverted(true);
    bottomRight.setInverted(true);
    topLeft.setInverted(false);
    bottomLeft.setInverted(false);

    topRight.configOpenloopRamp(Constants.RAMP_RATE);
    bottomRight.configOpenloopRamp(Constants.RAMP_RATE);
    topLeft.configOpenloopRamp(Constants.RAMP_RATE);
    bottomLeft.configOpenloopRamp(Constants.RAMP_RATE);

    leftEncoder.setDistancePerPulse((Math.PI * Constants.WHEEL_DIAM) / Constants.ENCODER_PPR);
    rightEncoder.setDistancePerPulse((Math.PI * Constants.WHEEL_DIAM) / Constants.ENCODER_PPR);
  }

  public void stop() {
    DriveTrain.arcadeDrive(0.0, 0.0);
  } 
 
  public void TeleopArcadeDrive(double speed, double rotation){
    double direction = 1.0;
    if (isReversed) {direction*=-1.0;}
    DriveTrain.arcadeDrive(direction * speed, rotation);
  }
  public void TeleopTankDrive(double leftSpeed, double rightSpeed){
    if (leftSpeed > Constants.MAX_SPEED){
        leftSpeed = Constants.MAX_SPEED;
    } else if (leftSpeed < -Constants.MAX_SPEED){
      leftSpeed = -Constants.MAX_SPEED;
    }
    if (rightSpeed > Constants.MAX_SPEED){
      rightSpeed = Constants.MAX_SPEED;
    } else if (rightSpeed < -Constants.MAX_SPEED){
      rightSpeed = -Constants.MAX_SPEED;
    }

    if (isReversed){
      DriveTrain.tankDrive(leftSpeed, rightSpeed);
    }else {
      DriveTrain.tankDrive(rightSpeed, leftSpeed);
    }
  }
  //Encoder methods
  public void resetEncoders(){
    leftEncoder.reset();
    rightEncoder.reset();
  }
    
  public double getAverageDistance(){
    return ((leftEncoder.getDistance() + rightEncoder.getDistance()) /2) + Constants.DIST_ADJUSTMENT;  
  }

  //ultrasonic sensor
  public double getDistance(){
    double rawValue = m_filter.calculate(ultrasonic.getValue());
    double voltage_scale_factor = 5/RobotController.getVoltage5V();
     // double currentDistanceCentimeters = rawValue * voltage_scale_factor * 0.125;
    double currentDistanceInches = rawValue * voltage_scale_factor * 0.0492;
    return currentDistanceInches;
  }

  //reverse drivetrain
  public void reverse() {
    if (isReversed == true){
      isReversed = false;
    } else {
      isReversed = true;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("UltrasonicDistance", getDistance());
  }
}