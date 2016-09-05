package com.quandoo.reservations.ui.tablechoosing;

import android.util.Log;

import com.quandoo.reservations.data.CustomerRepository;
import com.quandoo.reservations.data.TableDataSource;
import com.quandoo.reservations.data.TableRepository;
import com.quandoo.reservations.data.entities.Table;
import com.quandoo.reservations.data.entities.TableListWrapper;

import java.util.List;

/**
 * Created by felipe on 9/3/16.
 */
public class TableChoosingPresenter implements TableChoosingContract {

    private TableChoosingView tableChoosingView;
    private TableDataSource tableRepository;
    private CustomerRepository customerRepository;
    private Long customerId;
    private Integer reservedTableNumber;

    public TableChoosingPresenter(TableChoosingView tableChoosingView, Long customerId, Integer reservedTableNumber) {

        this.tableChoosingView = tableChoosingView;
        this.tableRepository = TableRepository.getInstance();
        this.customerRepository = CustomerRepository.getInstance();
        this.customerId = customerId;
        this.reservedTableNumber = reservedTableNumber;
    }

    @Override
    public void populateData() {

        tableRepository.loadTables(new TableDataSource.LoadData() {

            @Override
            public void onLoaded(TableListWrapper tableListWrapper) {


                TableListWrapper newTableListWrapper = new TableListWrapper();
                newTableListWrapper.setNewTableList(tableListWrapper.getTableList());

                if(reservedTableNumber != -1){
                    newTableListWrapper.getTableList().get(reservedTableNumber).setCurrentReserve(true);
                    newTableListWrapper.getTableList().get(reservedTableNumber).setStatus(false);
                }

                tableChoosingView.showTables(newTableListWrapper.getTableList());
            }

            @Override
            public void onNotAvailable() {
                Log.e("TableChoosingPresenter","Data not avaliable");
            }
        });

    }

    @Override
    public void reserveTable(List<Table> tableList, int selectedTablePosition) {

        Integer reservedTable = getReservedTable(tableList);

        if (reservedTable != null && reservedTable == selectedTablePosition) {

            setTableAvaliable(tableList, reservedTable);
        }
        else {

            if (tableList.get(selectedTablePosition).getStatus()) {

                if (reservedTable != null) {

                    setTableAvaliable(tableList, reservedTable);
                    setTableReserved(tableList, selectedTablePosition,customerId);
                }
                else {
                    setTableReserved(tableList, selectedTablePosition,customerId);
                }
            }
        }
    }

    private void setTableAvaliable(List<Table> tableList, Integer selectedTablePosition) {

        tableList.get(selectedTablePosition).setStatus(true);
        tableList.get(selectedTablePosition).setCurrentReserve(false);

        tableRepository.updateTable(selectedTablePosition,true);
        customerRepository.updateCustomer(customerId,null);

        tableChoosingView.unmarkTableAsReserved(tableList);
    }

    private void setTableReserved(List<Table> tableList, int selectedTablePosition, Long customerId) {

        tableList.get(selectedTablePosition).setStatus(false);
        tableList.get(selectedTablePosition).setCurrentReserve(true);

        tableRepository.updateTable(selectedTablePosition,false);
        customerRepository.updateCustomer(customerId,selectedTablePosition);

        tableChoosingView.markTableAsReserved(tableList);
    }

    private Integer getReservedTable(List<Table> tableList) {

        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getCurrentReserve()) {
                return i;
            }
        }
        return null;
    }

}
