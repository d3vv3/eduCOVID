// Username as default password
export const professor = [
  {
    id: 0,
    dni: "00000000Z",
    password: "00000000Z",
    center: "Universidad Politécnica de Madrid",
    name: "Miguel de Cervantes",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 1",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 2",
        healthState: "No Confinado",
        teachingState: "Presencial"
      }
    ]
  },
  {
    id: 1,
    dni: "00000000A",
    password: "federico21",
    center: "IES Gran Capitán",
    name: "Federico García Lorca",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 1",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 3",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 4",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 5",
        healthState: "Confinado",
        teachingState: "Online"
      }
    ]
  },
  {
    id: 2,
    dni: "00000000B",
    password: "jorge21",
    center: "Universidad Politécnica de Madrid",
    name: "Jorge Luis Borges",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 2",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 3",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 4",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 5",
        healthState: "Confinado",
        teachingState: "Online"
      }
    ]
  },
  {
    id: 3,
    dni: "00000000C",
    password: "julio21",
    center: "Universidad Carlos III",
    name: "Julio Cortázar",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 1",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 2",
        healthState: "No Confinado",
        teachingState: "Presencial"
      }
    ]
  },
  {
    id: 4,
    dni: "00000000D",
    password: "isabel21",
    center: "Universidad Carlos III",
    name: "Isabel Allende",
    state: "Confinado",
    groups: [
      {
        name: "Grupo 1",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 2",
        healthState: "No Confinado",
        teachingState: "Presencial"
      }
    ]
  }
];

export const students = [
  {
    id: 0,
    mat: "MAT0001",
    password: "MAT0001",
    center: "Universidad Politécnica de Madrid",
    name: "Sergio Almendro Cerdá",
    state: "No Confinado",
    group: {
      name: "Grupo 1",
      state: "Presencial"
    }
  },
  {
    id: 1,
    mat: "MAT0002",
    password: "javier21",
    center: "Universidad Politécnica de Madrid",
    name: "Javier Rodriguez Gallardo",
    state: "Confinado",
    group: {
      name: "Grupo 1",
      state: "Presencial"
    }
  },
  {
    id: 2,
    mat: "MAT0003",
    password: "jaime21",
    center: "Universidad Carlos III",
    name: "Jaime Conde Segovia",
    state: "Confinado",
    group: {
      name: "Grupo 2",
      state: "Online"
    }
  },
  {
    id: 3,
    mat: "MAT0004",
    password: "abejaro21",
    center: "Universidad Carlos III",
    name: "Javier Abejaro Capilla",
    state: "Confinado",
    group: {
      name: "Grupo 1",
      state: "Presencial"
    }
  },
  {
    id: 4,
    mat: "MAT0005",
    password: "alvaro21",
    center: "IES Gran Capitán",
    name: "Álvaro Martín Cortinas",
    state: "No Confinado",
    group: {
      name: "Grupo 3",
      state: "Online"
    }
  }
];

export const gruposBurbuja = [
  {
    name: "Grupo 1",
    state: "Confinado",
    students: ["1", "2", "3", "4", "5", "6"]
  },
  {
    name: "Grupo 2",
    state: "No Confinado",
    students: ["1", "2", "3", "4", "5", "6"]
  },
  {
    name: "Grupo 3",
    state: "No Confinado",
    students: ["1", "2", "3", "4", "5", "6"]
  },
  {
    name: "Grupo 4",
    state: "No Confinado",
    students: ["1", "2", "3", "4", "5", "6"]
  }
];

export const responsables = [
  {
    id: 0,
    dni: "00000000E",
    password: "santiago21",
    center: "Universidad Carlos III",
    name: "Santiago Ramón y Cajal"
  },
  {
    id: 1,
    dni: "00000000F",
    password: "severo21",
    center: "Universidad Politécnica de Madrid",
    name: "Severo Ochoa"
  },
  {
    id: 2,
    dni: "00000000G",
    password: "elvira21",
    center: "IES Gran Capitán",
    name: "Elvira Dávila Ortiz"
  }
];

export const clases = [
  {
    id: 1,
    nombre: "6ºA",
    fechaInicio: "12/05/2021",
    tiempoConmutacion: "15",
    groups: [
      {
        name: "Grupo 1",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 3",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 4",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 5",
        healthState: "Confinado",
        teachingState: "Online"
      }
    ]
  },

  {
    id: 2,
    nombre: "6ºB",
    fechaInicio: "12/05/2021",
    tiempoConmutacion: "15",
    groups: [
      {
        name: "Grupo 6",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 7",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 8",
        healthState: "No Confinado",
        teachingState: "Presencial"
      },
      {
        name: "Grupo 9",
        healthState: "Confinado",
        teachingState: "Online"
      }
    ]
  },

  {
    id: 3,
    nombre: "6ºB",
    fechaInicio: "12/05/2021",
    tiempoConmutacion: "15",
    groups: [
      {
        name: "Grupo 10",
        healthState: "Confinado",
        teachingState: "Online"
      },
      {
        name: "Grupo 11",
        healthState: "No Confinado",
        teachingState: "Presencial"
      }
    ]
  }
];
