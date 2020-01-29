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
    'icon': 'dashboard',
    'url': '/main/dashboard'
  },
  {
    'id': 'achievements',
    'title': 'Mein FreiwilligenLEBEN',
    'type': 'collapse',
    'icon': 'achievements',
    'children' : [
      {
        'id': 'achievements-management-summary',
        'title': 'Überblick',
        'icon': 'engagements',
        'type': 'item',
        'url': 'main/achievements/summary'
      },
      {
        'id': 'achievements-feuerwehr',
        'title': 'Freiwillige Feuerwehr',
        'icon': 'engagements',
        'type': 'item',
        'url': 'main/achievements/fireBrigade'
      },
      {
        'id': 'achievements-musikverein',
        'title': 'Musikverein',
        'icon': 'engagements',
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
    'icon': 'get-connected',
    'url': '/main/get-connected'
  }


];
