export const navigation_volunteer = [
  
  // {
  //   'id': 'digi',
  //   'title': 'Digitaler Freiwilligenpass',
  //   'type': 'group',
  //   // TODO own task page!
  //   // 'url': '/main/tasks/all',
  // },
  
  {
    'id': 'dashboard',
    'title': 'Mein FreiwilligenPASS',
    'type': 'item',
    'icon': 'dashboard2',
    'url': '/main/dashboard'
  },
  {
    'id': 'achievements',
    'title': 'Mein FreiwilligenLEBEN',
    'type': 'collapse',
    'icon': 'award2',
    'children' : [
      {
        'id': 'achievements-management-summary',
        'title': 'Überblick',
        'icon': 'home',
        'type': 'item',
        'url': 'main/achievements/summary'
      },
      {
        'id': 'achievements-feuerwehr',
        'title': 'Freiwillige Feuerwehr',
        'icon': 'home2',
        'type': 'item',
        'url': 'main/achievements/fireBrigade'
      },
      {
        'id': 'achievements-rotesKreuz',
        'title': 'Rotes Kreuz',
        'icon': 'home2',
        'type': 'item',
        'url': 'main/achievements/dummy'
      },
      {
        'id': 'achievements-musikverein',
        'title': 'Musikverein',
        'icon': 'home2',
        'type': 'item',
        'url': '/main/achievements/music'
      },
    ]

    
  },

  
  // {
  //   'id': 'digi',
  //   'title': 'Soziales Netzwerk',
  //   'type': 'group',
  // },
  {
    'id': 'get-connected',
    'title': 'Mein FreiwilligenNETZWERK',
    'type': 'item',
    'icon': 'people',
    'url': '/main/get-connected'
  }


];
