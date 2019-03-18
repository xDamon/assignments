CC = gcc
CCFLAGS = -Wall -Werror -pedantic -ansi
OBJ = main.o effects.o linked_list.o string_util.o file_util.o math_util.o commands.o coordinates.o

EXE = TurtleGraphics
EXE_SIMPLE = $(EXE)Simple
EXE_DEBUG = $(EXE)Debug

$(EXE_SIMPLE): CCFLAGS += -DSIMPLE
$(EXE_DEBUG): CCFLAGS += -DDEBUG

$(EXE): clean $(OBJ)
	$(CC) $(OBJ) -lm -o $(EXE)

$(EXE_SIMPLE): clean $(OBJ)
	$(CC) $(OBJ) -lm -o $(EXE_SIMPLE)

$(EXE_DEBUG): clean $(OBJ)
	$(CC) $(OBJ) -lm -o $(EXE_DEBUG)

main.o: main.c linked_list.h string_util.h file_util.h commands.h
	$(CC) $(CCFLAGS) -c main.c

effects.o: effects.c effects.h
	$(CC) $(CCFLAGS) -c effects.c

linked_list.o: linked_list.c linked_list.h
	$(CC) $(CCFLAGS) -c linked_list.c

string_util.o: string_util.c boolean.h
	$(CC) $(CCFLAGS) -c string_util.c

file_util.o: file_util.c file_util.h coordinates.h
	$(CC) $(CCFLAGS) -c file_util.c

math_util.o: math_util.c
	$(CC) $(CCFLAGS) -c math_util.c

commands.o: commands.c commands.h linked_list.h effects.h string_util.h file_util.h math_util.h boolean.h coordinates.h 
	$(CC) $(CCFLAGS) -c commands.c

coordinates.o: coordinates.c coordinates.h math_util.h
	$(CC) $(CCFLAGS) -c coordinates.c

debug: $(EXE_DEBUG)
simple: $(EXE_SIMPLE)

clean:
	rm -f $(OBJ) $(EXE) $(EXE_SIMPLE) $(EXE_DEBUG)
