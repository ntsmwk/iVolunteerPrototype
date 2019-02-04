import { UserDefinedTaskTemplateDetailFormModule } from './user-defined-task-template-detail-form.module';

describe('UserDefinedTaskTemplateDetailFormModule', () => {
  let userDefinedTaskTemplateDetailFormModule: UserDefinedTaskTemplateDetailFormModule;

  beforeEach(() => {
    userDefinedTaskTemplateDetailFormModule = new UserDefinedTaskTemplateDetailFormModule();
  });

  it('should create an instance', () => {
    expect(userDefinedTaskTemplateDetailFormModule).toBeTruthy();
  });
});
