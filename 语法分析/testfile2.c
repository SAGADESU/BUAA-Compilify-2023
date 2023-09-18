#include <stdio.h>

int a = 1, b = 2;
int c = 0;
int d = 5 % 4;
int e = !-1;

void testFor()
{
    int i, j;
    for (i = 0; i < 3; i = i + 1)
    {
        for (j = 0; j < 3; j = j + 1)
        {
        }
    }
    for (;;)
    {
        break;
    }
    for (i = 0;;)
    {
        break;
    }
    for (; i < 10;)
    {
        break;
    }
    for (;; i = i + 1)
    {
        break;
    }
    for (i = 0; i < 10;)
    {
        i = i + 1;
        continue;
    }
    for (; i < 10; i = i + 1)
    {
        ;
    }
    for (i = 0;; i = i + 1)
    {
        if (i > 10)
        {
            break;
        }
    }
}


void testLogicAnd()
{
    if (a == 1 && b == 2)
    {
        printf("a=%d,b=%d,condition is a==1&&b==2\n", a, b);
    }
    return;
}

void testLoginOr()
{
    if (a == 1 || b != 3)
    {
        printf("a=%d,b=%d,condition is a==1||b!=3\n", a, b);
        printf("%d\n", e);
    }
}

int testCompare()
{
    if (a > b)
    {
        printf("a>b\n");
    }
    else if (a < b)
    {
        printf("a<b\n");
    }

    if (c >= d)
    {
        printf("c>=d\n");
    }
    if (c <= d)
    {
        printf("c<=d\n");
    }
}

int main()
{
    printf("21373328\n");
    testFor();
    testLogicAnd();
    testLoginOr();
    testCompare();
    return 0;
}