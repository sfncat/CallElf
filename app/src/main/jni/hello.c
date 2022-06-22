#include<stdio.h>

int main(int argc, char *argv[]){
    if (argc >1) {
        printf("你好世界, i'm hello.c %s\n", argv[1]);
    } else {
        printf("你好世界, i'm hello.c\n");
    }

    return 0;
}
