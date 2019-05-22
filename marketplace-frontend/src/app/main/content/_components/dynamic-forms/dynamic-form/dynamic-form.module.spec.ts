import { DynamicFormModule } from './dynamic-form.module';

describe('DynamicFormModule', () => {
  let dynamicFormModule: DynamicFormModule;

  beforeEach(() => {
    dynamicFormModule = new DynamicFormModule();
  });

  it('should create an instance', () => {
    expect(dynamicFormModule).toBeTruthy();
  });
});
