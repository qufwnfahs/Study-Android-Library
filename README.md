# 1. Android Architecture Component
## 1) Android JetPack Navigation
- Application 내의 화면 전환을 좀 더 쉽게 구현하고 화면 흐름을 시각화해서 볼 수 있도록 해주는 Framework이다.
- 기존의 startActivity()나 FragmentManager를 이용한 화면 전환보다 구현이 더 단순화
     
```
   findNavController().navigate(R.id.action_f1_to_f2)
```
### (1) Navigation
- Navigation File(navigation_test.xml 생성)
- 시작 지점을 정의 (app:startDestination="@id/blankFragment")
- Destination마다 Action을 정의

```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools" android:id="@+id/navigation_test"
            app:startDestination="@id/blankFragment1"> // 이 navigation의 시작 지점을 정함

   <fragment android:id="@+id/blankFragment1" android:name="com.example.myapplication.BlankFragment1"
             android:label="fragment_blank_fragment1" tools:layout="@layout/fragment_blank_fragment1">
      <action android:id="@+id/action_blankFragment1_to_blankFragment2" app:destination="@id/blankFragment2"/>
   </fragment>
   <fragment android:id="@+id/blankFragment2" android:name="com.example.myapplication.BlankFragment2"
             android:label="fragment_blank_fragment2" tools:layout="@layout/fragment_blank_fragment2"/>
</navigation>
```

### (2) NavHostFragment
- NavHostFragment는 Navigation을 보여주는 영역을 제공한다. (NavigationView)

- NavHostFragment 구성요소

```
<androidx.drawerlayout.widget.DrawerLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <fragment
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/my_nav_host_fragment"
            android:name="androidx.navigation.fragment.NavHostFragment"
            app:navGraph="@navigation/nav_sample"
            app:defaultNavHost="true" />
    <android.support.design.widget.NavigationView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="start"/>
 </androidx.drawerlayout.widget.DrawerLayout>
```
- 각 NavHostFragment는 Navigation에서 정의한 action들을 컨트롤하는 NavController를 가진다.
- NavController는 Navigation Graph와 현재 위치 및 NavHostFragment 자체와 함께 저장 및 복원될 백 스택과 같은 탐색 상태가 포함된다.

- NavController 얻는법

```           
1. NavHostFragment.findNavController(Fragment)
2. Navigation.findNavController(Activity, @IdRes int viewId)
3. Navigation.findNavController(View)
```
- app:NavGraph 속성을 통해 Navigation Graph를 설정하고, app:defaultNavHost="true"를 통해 System의 back button 이벤트를 intercept함.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

    <fragment
            android:name="androidx.navigation.fragment.NavHostFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent" app:navGraph="@navigation/navigation_test" app:defaultNavHost="true"
            android:id="@+id/fragment"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### (3) Destination 간 Data 전달
1] Bundle 방식 (기존 Activity, Fragment간 data 전달 방식)
- argument tag를 이용해 Destination의 data를 정의한다. (navigation_test.xml)
     
```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools" android:id="@+id/navigation_test"
            app:startDestination="@id/blankFragment1"> // 이 navigation의 시작 지점을 정함

   <fragment android:id="@+id/blankFragment1" android:name="com.example.myapplication.BlankFragment1"
             android:label="fragment_blank_fragment1" tools:layout="@layout/fragment_blank_fragment1">
      <action android:id="@+id/action_blankFragment1_to_blankFragment2" app:destination="@id/blankFragment2"/>
      <argument android:name="customer" app:argType="string" android:defaultValue="Unknown"/>
   </fragment>
   <fragment android:id="@+id/blankFragment2" android:name="com.example.myapplication.BlankFragment2"
             android:label="fragment_blank_fragment2" tools:layout="@layout/fragment_blank_fragment2"/>
</navigation>
```
- Bundle 생성

```
var bundle : Bundle = Bundle()
bundle.putString("customer", "Input yout text")
navController().navigatie(R.id_action, bundle)
```

2] safeargs 플러그인 (type-safe)
 - app/build.gradle에 정의한 safeargs.plugin을 이용해서 data 전달 
