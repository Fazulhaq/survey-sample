import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface StepIndex {
  stepperIndex: number;
}

const initialState: StepIndex = {
  stepperIndex: 0,
};

const IndexSlice = createSlice({
  name: 'index',
  initialState,
  reducers: {
    incrementIndex: (state, action: PayloadAction<number>) => {
      state.stepperIndex += action.payload;
    },
    decrementIndex: (state, action: PayloadAction<number>) => {
      state.stepperIndex -= action.payload;
    },
  },
});

export const { incrementIndex, decrementIndex } = IndexSlice.actions;

export default IndexSlice.reducer;
