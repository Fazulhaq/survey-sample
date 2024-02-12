import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface StepIndex {
  surveyEditIndex: number;
}

const initialState: StepIndex = {
  surveyEditIndex: 0,
};

const SurveyEditIndexSlice = createSlice({
  name: 'surveyeditindex',
  initialState,
  reducers: {
    incrementEditIndex: (state, action: PayloadAction<number>) => {
      state.surveyEditIndex += action.payload;
    },
    decrementEditIndex: (state, action: PayloadAction<number>) => {
      state.surveyEditIndex -= action.payload;
    },
    resetEditIndex: (state, action: PayloadAction<number>) => {
      state.surveyEditIndex = action.payload;
    },
  },
});

export const { incrementEditIndex, decrementEditIndex, resetEditIndex } = SurveyEditIndexSlice.actions;

export default SurveyEditIndexSlice.reducer;
