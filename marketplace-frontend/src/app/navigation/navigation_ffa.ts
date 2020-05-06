export const navigation_ffa = [
  {
    id: 'welcome',
    title: 'Übersicht',
    type: 'item',
    icon: 'door-open',
    url: '/main/dashboard',
  },
  {
    id: 'digi',
    title: 'Digitalisieren',
    type: 'group',
  },
  {
    id: 'tasks',
    title: 'Einträge erfassen',
    type: 'item',
    icon: 'pencil',
    url: '/main/tasks/all',
  },
  {
    id: 'tasks',
    title: 'Einträge importieren',
    type: 'item',
    icon: 'import',
    url: '/main/import',
  },
  {
    id: 'inbox',
    title: 'Einträge bestätigen',
    type: 'item',
    icon: 'unlock',
    url: '/main/helpseeker/asset-inbox',
  },
  {
    id: 'config',
    title: 'Konfigurieren',
    type: 'group',
  },
  {
    id: 'configurator',
    title: 'Klassen konfigurieren',
    type: 'item',
    icon: 'cogs',
    url: '/main/configurator',
  },
  {
    id: 'rule-configurator',
    title: 'Einträge ableiten',
    type: 'item',
    icon: 'share-square',
    url: '/main/rules/all',
  },
  {
    'id': 'matching-configurator',
    'title': 'Matching konfigurieren',
    'type': 'item',
    'icon': 'cogs',
    'url': '/main/matching-configurator/'
  },
  {
    'id': 'property-builder',
    'title': 'Property-Baukasten',
    'type': 'item',
    'icon': 'cogs',
    'url': '/main/property-builder/'
  },


];
