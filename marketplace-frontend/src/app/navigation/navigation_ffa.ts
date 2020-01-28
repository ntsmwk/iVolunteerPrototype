export const navigation_ffa = [
  {
    'id': 'welcome',
    'title': 'Übersicht',
    'type': 'item',
    'url': '/main/dashboard',
  },
  {
    'id': 'digi',
    'title': 'Digitalisieren',
    'type': 'group',
  },
  {
    'id': 'tasks',
    'title': 'Einträge Erfassen',
    'type': 'item',
    'url': '/main/tasks/all',
  },
  {
    'id': 'inbox',
    'title': 'Einträge Bestätigen',
    'type': 'item',
    'url': '/main/helpseeker/asset-inbox',
  },

  {
    'id': 'config',
    'title': 'Konfigurieren',
    'type': 'group',
    // TODO own task page!
    // 'url': '/main/tasks/all',
  },
  {
    'id': 'configurator',
    'title': 'Einträge Konfigurieren',
    'type': 'item',
    'url': '/main/configurator'
  },
  {
    'id': 'rule-configurator',
    'title': 'Einträge Ableiten',
    'type': 'item',
    'url': '/main/rules/all'
  },
];
