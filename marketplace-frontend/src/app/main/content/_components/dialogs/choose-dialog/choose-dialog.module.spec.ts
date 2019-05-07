import { ChooseDialogModule } from './choose-dialog.module';

describe('ChooseDialogModule', () => {
  let chooseDialogModule: ChooseDialogModule;

  beforeEach(() => {
    chooseDialogModule = new ChooseDialogModule();
  });

  it('should create an instance', () => {
    expect(chooseDialogModule).toBeTruthy();
  });
});
