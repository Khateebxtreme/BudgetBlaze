import * as React from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';

const steps = [
  'Step 1: Register User',
  'Step 2: OTP Verification',
  'Step 3: User Successfull Registration and Verificaiton'
];

const Form_Stepper = ({step_count}) => {
  return (
    <Box sx={{ width: '70%' }}>
    <Stepper activeStep={step_count} alternativeLabel>
      {steps.map((label) => (
        <Step key={label}>
          <StepLabel>{label}</StepLabel>
        </Step>
      ))}
    </Stepper>
  </Box>
  );
}

export default Form_Stepper