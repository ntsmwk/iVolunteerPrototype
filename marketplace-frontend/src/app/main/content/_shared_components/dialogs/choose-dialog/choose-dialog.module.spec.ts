import { ChooseTemplateToCopyDialogModule } from './choose-dialog.module';

describe('ChooseDialogModule', () => {
  let chooseDialogModule: ChooseTemplateToCopyDialogModule;

  beforeEach(() => {
    chooseDialogModule = new ChooseTemplateToCopyDialogModule();
  });

  it('should create an instance', () => {
    expect(chooseDialogModule).toBeTruthy();
  });
});
