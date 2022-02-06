#include<iostream>
using namespace std;
int main(){
	int n;
	cin>>n;
	if(n==1) cout<<1<<endl;
	else if(n==2) cout<<2<<endl;
	else if(n==3) cout<<3<<endl;
	else if(n==4) cout<<4<<endl;
	else {	
	int ans[n+1];	
	ans[0]=0;ans[1]=1; ans[2]=2; ans[3]=3; ans[4]=4;
	
	for(int i=5;i<=n;i++)
	{		
		int tmp=0;
		for(int j=1;j<=i-1;j++)
		{
			if(j*ans[i-j]>tmp) tmp=j*ans[i-j];
		}
	
	ans[i]=tmp;		
	}	
	cout<<ans[n]<<endl;		
	}
	return 0;
	
}