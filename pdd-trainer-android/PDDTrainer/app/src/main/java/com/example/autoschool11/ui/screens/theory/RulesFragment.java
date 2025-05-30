package com.example.autoschool11.ui.screens.theory;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.databinding.FragmentRulesBottomBinding;
import com.example.autoschool11.ui.adapters.TheoryAdapter;


public class RulesFragment extends Fragment implements TheoryAdapter.HomeClickListener, View.OnClickListener {

    Context context;
    protected FragmentRulesBottomBinding binding;
    int[] images = {R.drawable.ic_action_fines1, R.drawable.ic_action_signs1, R.drawable.ic_action_medicine, R.drawable.ic_action_police,R.drawable.ic_action_question,  R.drawable.ic_action_plate12};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRulesBottomBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.pddCard.setOnClickListener(this);
        String[] home = getResources().getStringArray(R.array.home);
        binding.homeRV.setEnabled(false);
        TheoryAdapter theoryAdapter = new TheoryAdapter(home, images, this);
        binding.homeRV.setLayoutManager(gridLayoutManager);
        binding.homeRV.setAdapter(theoryAdapter);
        return view;
    }

    @Override
    public void onHomeClick(int position) { // обработка нажатия на элемент в списке
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        switch (position) {
            case 0:
                navController.navigate(R.id.finesFragment);
                break;
            case 1:
                navController.navigate(R.id.signsFragment);
                break;
            case 2:
                navController.navigate(R.id.medicineFragment);
                break;
            case 3:
                navController.navigate(R.id.talkingFragment);
                break;
            case 4:
                navController.navigate(R.id.examRulesFragment);
                break;
            case 5:
                navController.navigate(R.id.regionCodesFragment);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.rulesFragmentPDD2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

/*
        0 - Ограничение скорости 20 км/ч
1 - Ограничение скорости 30 км/ч
2 - Ограничение скорости 50 км/ч
3 - Ограничение скорости 60 км/ч
4 - Ограничение скорости 70 км/ч
5 - Ограничение скорости 80 км/ч
6 - конец Ограничение скорости 80 км/ч
7 - Ограничение скорости 100 км/ч
8 - Ограничение скорости 120 км/ч
9 - обгон автомобилям запрещен
10 - обгон грузовым автомобилям запрещен
11 - Пересечение со второстепенной дорогой
12 - Главная дорога
13 - Уступи дорогу
14 - Движение без остановки запрещено
15 - Движение запрещено
16 - Движение грузовых средств запрещено
17 - Въезд запрещен (Кирпич)
18 - Прочие опасности
19 - Опасный поворот влево
20 - Опасный поворот вправо
21 - Опасные повороты сначала влево, потом вправо
22 - Неровная дорога
23 - Скользкая дорога
24 - Сужение дороги справа (предупреждающий знак)
25 - Дорожные работы
26 - Светофорное регулирование
27 - Пешеходный переход (предупреждающий)
28 - Дети
29 - Пересечение с велосипедной дорожкой
30 - Снежинка
31 - Дикие животные
32 - Конец всех ограничений
33 - Движение направо
34 - Движение налево
35 - Движение прямо
36 - Движение прямо и направо
37 - Движение прямо и налево
*/
