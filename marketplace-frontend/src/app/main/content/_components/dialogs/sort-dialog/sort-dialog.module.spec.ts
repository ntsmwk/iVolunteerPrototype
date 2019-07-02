import { SortDialogModule } from './sort-dialog.module';

describe('AddOrRemoveDialogModule', () => {
  let sortDialogModule: SortDialogModule;

  beforeEach(() => {
    sortDialogModule = new SortDialogModule();
  });

  it('should create an instance', () => {
    expect(sortDialogModule).toBeTruthy();
  });
});
