import { FuseUserDefinedTaskTemplateListModule } from './user-defined-task-template-list.module';

describe('UserDefinedTaskTemplateListModule', () => {
  let userDefinedTaskTemplateListModule: FuseUserDefinedTaskTemplateListModule;

  beforeEach(() => {
    userDefinedTaskTemplateListModule = new FuseUserDefinedTaskTemplateListModule();
  });

  it('should create an instance', () => {
    expect(userDefinedTaskTemplateListModule).toBeTruthy();
  });
});
