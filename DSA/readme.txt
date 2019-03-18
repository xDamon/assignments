RUNNING THE PROGRAM:
    The python version used is the same as the one that comes with the
    anaconda installation which is 3.6.5. The program will definitely not work with
    python 2.7 so please check that the right version of python is being used as
    I am pretty sure both are installed on lab machines.

    To run the program type:
        python ./src/main.py

    To run the tests type:
        python ./tests/test.py

    It is important for the current working directory to be the same one as this
    readme file is in.

DEPENDENCIES:
    numpy

FILES:
│   main.py
│   
├───election
│       Candidate.py
│       Division.py
│       Election.py
│       Party.py
│       State.py
│       
├───options
│   │   itineraryByMargin.py
│   │   listByMargin.py
│   │   listNominees.py
│   │   nomineeSearch.py
│   │   
│   └───common
│           filters.py
│           sorts.py
│           
├───structures
│       ArrayList.py
│       Graph.py
│       HashTable.py
│       
└───util
    │   Menu.py
    │   Report.py
    │   
    └───functions
            comparator.py
            permutations.py
            prime.py
            sort.py
            time.py
   