import { UserDefinedTaskTemplateListModule } from './user-defined-task-template-list.module';

describe('UserDefinedTaskTemplateListModule', () => {
  let userDefinedTaskTemplateListModule: UserDefinedTaskTemplateListModule;

  beforeEach(() => {
    userDefinedTaskTemplateListModule = new UserDefinedTaskTemplateListModule();
  });

  it('should create an instance', () => {
    expect(userDefinedTaskTemplateListModule).toBeTruthy();
  });
});
