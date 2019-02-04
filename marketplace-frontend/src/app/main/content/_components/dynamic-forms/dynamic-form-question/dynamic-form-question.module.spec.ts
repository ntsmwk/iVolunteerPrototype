import { DynamicFormQuestionModule } from './dynamic-form-question.module';

describe('DynamicFormQuestionModule', () => {
  let dynamicFormQuestionModule: DynamicFormQuestionModule;

  beforeEach(() => {
    dynamicFormQuestionModule = new DynamicFormQuestionModule();
  });

  it('should create an instance', () => {
    expect(dynamicFormQuestionModule).toBeTruthy();
  });
});
