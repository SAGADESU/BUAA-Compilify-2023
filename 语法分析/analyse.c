#include <stdio.h>
const int a = 1, b = -2, c = 0;
const int d = 1234567;
const int e = 123456, f = (1+2+3), g = 40+15;
const int h = (12 + 12)*6 + 0, x = (1 + (-2)) * 0 * (0 + 1);
const int __ab1cdefg__123 = -123;
const int abc_0_defg = (30 + 50)/4;
const int y = -40/-2;
const int z = (+11) % 3;

const int array1[5] = {1, 2, 3, 4, 5}, array2[10] = {10, 11, 12, 13, -14, -15, -16, 17, 0, 0};
const int array[2][2] = {{1,2},{3,4}};
int array3[2][6/2] = {{1, -0, 0}, {10/5, 6 % 4 + 5, 0}};
int array4[4];
int array5[4] = {1, -1, 0, 4};

int aaa, bbb, ccc;
int xx, aaaa = 123, bbbb = -123, cccc = +0;


//This is an explanatory note.
/*
	test1
	test2
	test3
*/

int compare(int a, int b) {
	if (a > b) return a;
	return b;
}

int func1(int a[]) {
	return 12;
}

int func2(int a, int b[]) {
	return -10;
}

int func3(int a,int b, int c[], int d[][4]) {
	return 0;
}

void test_exp() {
	int a[4] = {array1[0] * 15, 2 - array2[5], 3, array1[3]};
	int b[4][4];
	int c[1][4] = {{1, 2, 3, 4}};
	array3[0][0] * 3;
	a[0] = 1;
	a[0] = +(-(+(-(+array1[3]) * 122) * 12) % 10);
//	a[array1[0]] = -compare(array2[4]*9-100/2, array2[6]*-13);
	a[array1[array2[9]]] = 1;
	b[array1[0]][array1[0]] = array2[5] * 5;
//	b[2][3] = b[array1[0]][array1[0]] - 6 * 2 / 7 +12;
//	b[0][0 * b[2][3]] = -(-(-(+b[2][3] * 19)/3) % 15);
//	+(-(+(-(+b[0][1-1]*b[1][1])*b[1][1])/b[2][3]))%b[2][3];
//	(((compare(b[0][0], b[1][1])*b[2][6/2])+b[2][3])*-b[2][3]);
	int xxx = func1(a) * 234/45;
//?	int yyy = func2(a[0], c[0]);
//	int zzz = func3(b[1][1], a[0], b[0], b) % (23);
}


void test_params3(int a) {
	return ;
}

void test_params2(int a[], int b[][3]) {
	return ;
}

void test_getint() {
	int n1, n2, n3;
	scanf("%d",&n1);
	scanf("%d",&n2);
	scanf("%d",&n3);
	return ;
}

void test_printf() {
	int n1 = 100, n2 = -25, n3 = 10;
	printf("n1 = %d\n", n1);
	printf("n2-5 = %d\n", n2-5);
	printf("n2 + n3 = %d\n", n2 + n3);
	printf("n2 * n3 = %d\n", n2 * n3);
	printf("! ()*+,-./0123456789:;\n");
	printf("<=>?@\n");
	printf("ABCDEFGHIJKLMNOPQRSTUVWXYZ\n");
	printf("[]^_`\n");
	printf("abcdefghijklmnopqrstuvwxyz{|}~\n");
	return ;
}

int test_return() {
	return 1+1*2;
}

int test_params1(int a, int b, int c) {
	return a + b * c * a;
}

int main() {
	test_getint();
	printf("21373328\n");
	test_printf();
	test_return();
//	test_exp();
	test_params1(a, d, f);
	test_params2(array3[1], array3);
	test_params3(123);

	return 0;
}