import { AddOrRemoveDialogModule } from './add-or-remove-dialog.module';

describe('AddOrRemoveDialogModule', () => {
  let addOrRemoveDialogModule: AddOrRemoveDialogModule;

  beforeEach(() => {
    addOrRemoveDialogModule = new AddOrRemoveDialogModule();
  });

  it('should create an instance', () => {
    expect(addOrRemoveDialogModule).toBeTruthy();
  });
});
