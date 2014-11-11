<?php echo $this->Form->create('Combination'); ?>
<div class="box box-primary">
            <div class="box-header">
                <!--<h3 class="box-title">Quick Example</h3>-->
            </div><!-- /.box-header -->
            <!-- form start -->
            <div class="box-body">
                    <div class="form-group">
                        <?php echo $this->Form->input("vendor_id",array(
                                    'div'=>false,
                                    'options' => array(),
                                    'class'=>'form-control',
                                    'placeholder' => 'Enter Username',
                                    'data-bind'=> "value:vendor, options: dataV, optionsText: 'name', optionsValue: 'id'"
                                )); 
                              
                        ?>
                    </div>
                    <div class="form-group">
                        <?php echo $this->Form->input("recipe_id",array(
                                    'div'=>false,
                                    'options' => array(),
                                    'class'=>'form-control',
                                    'placeholder' => 'Enter Username',
                                    'multiple'=>'multiple',
                                    'data-bind'=> "selectedOptions:recipes, options: dataR, optionsText: 'recipe_name', optionsValue: 'id'"
                                )); 
                              
                        ?>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary" data-bind="click:generate">Generate Combinations</button>
                    </div>
                    <div class="form-group">
                        <table class="table-bordered table">
                            <tbody data-bind="foreach: generatedD">
                                <tr>
                                    <td><input type="checkbox" name="combinations[]" /></td>
                                    <td data-bind="text:display_name"></td>
                                    <td>Price: <input class="pull-right" type="text" value="0.00" data-bind="value:price" /></td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary" data-bind="click:send">Add</button>
                    </div>
            </div>
</div>
<?php echo $this->Form->end(__('Submit')); ?>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/knockout/3.2.0/knockout-min.js"></script>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/knockout.mapping/2.4.1/knockout.mapping.js"></script>
<script type="text/javascript">
    var CombinationsVM = function(){
        var me  = this;
        me.dataR = <?php echo json_encode($recipes); ?>;
        me.dataV = <?php echo json_encode($vendors); ?>;
        me.recipes = ko.observableArray([]);
        me.vendor = ko.observable();
        me.generatedD = ko.observableArray([]);
        me.key = function(a,b){
                if(a>b)
                    return b + '-' + a;
                return a + '-' + b;
        }
        me.keyAr = [];
        me.generate = function(){
            var r = me.recipes();
            var r2 = me.recipes();
            if(r.length < 2){
                alert("Please select at least two recipes...");
                return false;
            }
            
            me.generatedD([]);
            for(i in r){
                var name = $.grep(cbObj.dataR, function(e){ return e.id == r[i]; });
                name = name[0].recipe_name;
                me.generatedD.push({
                    key: r[i],
                    vendor_id : me.vendor(),
                    display_name: "FULL " + name,
                    price: ko.observable(0.00),
                    CombinationItem: [
                        $.grep(cbObj.dataR, function(e){ return e.id == r[i]; })
                    ]
                });
                for(j in r2){
                    if(r2[j] == r[i])
                        continue;
                    var name2 = $.grep(cbObj.dataR, function(e){ return e.id == r2[j]; });
                    name2 = name2[0].recipe_name;
                    if($.inArray(me.key(r[i],r2[j]),me.keyAr) == false){
                        me.generatedD.push({
                            key: me.key(r[i].id,r2[j]),
                            vendor_id : me.vendor(),
                            display_name: "HALF " + name + " + HALF " + name2,
                            price: ko.observable(0.00),
                            CombinationItem: [ 
                                $.grep(cbObj.dataR, function(e){ return e.id == r[i]; }),
                                $.grep(cbObj.dataR, function(e){ return e.id == r2[j]; })
                            ]

                        });
                    }
                    me.keyAr.push(me.key(r[i],r2[j]));
                    
                }
            }
            
        }
        me.send = function(){
            var data = ko.mapping.toJS(me.generatedD);
            console.dir(data);
        }
        
    }
    var cbObj = new CombinationsVM();
    $(document).ready(function(){
        ko.applyBindings(cbObj);
    });
</script>    