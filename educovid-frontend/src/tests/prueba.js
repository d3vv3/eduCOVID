// Username as default password
export const professor = [
  {
    id: 0,
    dni: "00000000Z",
    password: "00000000Z",
    center: "Universidad Politécnica de Madrid",
    name: "Javier Rodríguez Gallardo",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 1",
        state: "Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      },
      {
        name: "Grupo 2",
        state: "No Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      }
    ]
  },
  {
    id: 1,
    dni: "00000000A",
    password: "alvaro21",
    center: "IES Gran Capitán",
    name: "Álvaro Martín Cortinas",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 1",
        state: "Confinado",
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
      },
      {
        name: "Grupo 5",
        state: "Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      }
    ]
  },
  {
    id: 2,
    dni: "00000000B",
    password: "jaime21",
    center: "Universidad Politécnica de Madrid",
    name: "Jaime Conde Segovia",
    state: "No Confinado",
    groups: [
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
      },
      {
        name: "Grupo 5",
        state: "Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      }
    ]
  },
  {
    id: 3,
    dni: "00000000C",
    password: "sergio21",
    center: "Universidad Carlos III",
    name: "Sergio Almendro Cerdá",
    state: "No Confinado",
    groups: [
      {
        name: "Grupo 1",
        state: "Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      },
      {
        name: "Grupo 2",
        state: "No Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      }
    ]
  },
  {
    id: 4,
    dni: "00000000D",
    password: "abejaro21",
    center: "Universidad Carlos III",
    name: "Javier Abejaro Capilla",
    state: "Confinado",
    groups: [
      {
        name: "Grupo 1",
        state: "Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
      },
      {
        name: "Grupo 2",
        state: "No Confinado",
        students: ["1", "2", "3", "4", "5", "6"]
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
    state: "No confinado",
    group: {
      name: "Grupo 3",
      state: "Online"
    }
  }
];

export const bubbleGroups = [
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
